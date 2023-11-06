import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('InfoPageCategory e2e test', () => {
  const infoPageCategoryPageUrl = '/info-page-category';
  const infoPageCategoryPageUrlPattern = new RegExp('/info-page-category(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const infoPageCategorySample = { value: 'USB', language: 'GREEK' };

  let infoPageCategory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/info-page-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/info-page-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/info-page-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (infoPageCategory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/info-page-categories/${infoPageCategory.id}`,
      }).then(() => {
        infoPageCategory = undefined;
      });
    }
  });

  it('InfoPageCategories menu should load InfoPageCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('info-page-category');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InfoPageCategory').should('exist');
    cy.url().should('match', infoPageCategoryPageUrlPattern);
  });

  describe('InfoPageCategory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(infoPageCategoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InfoPageCategory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/info-page-category/new$'));
        cy.getEntityCreateUpdateHeading('InfoPageCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', infoPageCategoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/info-page-categories',
          body: infoPageCategorySample,
        }).then(({ body }) => {
          infoPageCategory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/info-page-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/info-page-categories?page=0&size=20>; rel="last",<http://localhost/api/info-page-categories?page=0&size=20>; rel="first"',
              },
              body: [infoPageCategory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(infoPageCategoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InfoPageCategory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('infoPageCategory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', infoPageCategoryPageUrlPattern);
      });

      it('edit button click should load edit InfoPageCategory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InfoPageCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', infoPageCategoryPageUrlPattern);
      });

      it('edit button click should load edit InfoPageCategory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InfoPageCategory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', infoPageCategoryPageUrlPattern);
      });

      it('last delete button click should delete instance of InfoPageCategory', () => {
        cy.intercept('GET', '/api/info-page-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('infoPageCategory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', infoPageCategoryPageUrlPattern);

        infoPageCategory = undefined;
      });
    });
  });

  describe('new InfoPageCategory page', () => {
    beforeEach(() => {
      cy.visit(`${infoPageCategoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InfoPageCategory');
    });

    it('should create an instance of InfoPageCategory', () => {
      cy.get(`[data-cy="value"]`).type('Engineer').should('have.value', 'Engineer');

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        infoPageCategory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', infoPageCategoryPageUrlPattern);
    });
  });
});
