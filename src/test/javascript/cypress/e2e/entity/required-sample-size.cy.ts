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

describe('RequiredSampleSize e2e test', () => {
  const requiredSampleSizePageUrl = '/required-sample-size';
  const requiredSampleSizePageUrlPattern = new RegExp('/required-sample-size(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requiredSampleSizeSample = { value: 'B2C', language: 'ENGLISH' };

  let requiredSampleSize;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/required-sample-sizes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/required-sample-sizes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/required-sample-sizes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requiredSampleSize) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/required-sample-sizes/${requiredSampleSize.id}`,
      }).then(() => {
        requiredSampleSize = undefined;
      });
    }
  });

  it('RequiredSampleSizes menu should load RequiredSampleSizes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('required-sample-size');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequiredSampleSize').should('exist');
    cy.url().should('match', requiredSampleSizePageUrlPattern);
  });

  describe('RequiredSampleSize page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requiredSampleSizePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequiredSampleSize page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/required-sample-size/new$'));
        cy.getEntityCreateUpdateHeading('RequiredSampleSize');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requiredSampleSizePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/required-sample-sizes',
          body: requiredSampleSizeSample,
        }).then(({ body }) => {
          requiredSampleSize = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/required-sample-sizes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/required-sample-sizes?page=0&size=20>; rel="last",<http://localhost/api/required-sample-sizes?page=0&size=20>; rel="first"',
              },
              body: [requiredSampleSize],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(requiredSampleSizePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequiredSampleSize page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requiredSampleSize');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requiredSampleSizePageUrlPattern);
      });

      it('edit button click should load edit RequiredSampleSize page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequiredSampleSize');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requiredSampleSizePageUrlPattern);
      });

      it('edit button click should load edit RequiredSampleSize page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequiredSampleSize');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requiredSampleSizePageUrlPattern);
      });

      it('last delete button click should delete instance of RequiredSampleSize', () => {
        cy.intercept('GET', '/api/required-sample-sizes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('requiredSampleSize').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requiredSampleSizePageUrlPattern);

        requiredSampleSize = undefined;
      });
    });
  });

  describe('new RequiredSampleSize page', () => {
    beforeEach(() => {
      cy.visit(`${requiredSampleSizePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequiredSampleSize');
    });

    it('should create an instance of RequiredSampleSize', () => {
      cy.get(`[data-cy="value"]`).type('markets lime Chicken').should('have.value', 'markets lime Chicken');

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        requiredSampleSize = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requiredSampleSizePageUrlPattern);
    });
  });
});
