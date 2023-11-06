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

describe('CoreProductElements e2e test', () => {
  const coreProductElementsPageUrl = '/core-product-elements';
  const coreProductElementsPageUrlPattern = new RegExp('/core-product-elements(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const coreProductElementsSample = { value: 'Handcrafted grid-enabled integrate', language: 'ENGLISH' };

  let coreProductElements;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/core-product-elements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/core-product-elements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/core-product-elements/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (coreProductElements) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/core-product-elements/${coreProductElements.id}`,
      }).then(() => {
        coreProductElements = undefined;
      });
    }
  });

  it('CoreProductElements menu should load CoreProductElements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('core-product-elements');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CoreProductElements').should('exist');
    cy.url().should('match', coreProductElementsPageUrlPattern);
  });

  describe('CoreProductElements page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(coreProductElementsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CoreProductElements page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/core-product-elements/new$'));
        cy.getEntityCreateUpdateHeading('CoreProductElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', coreProductElementsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/core-product-elements',
          body: coreProductElementsSample,
        }).then(({ body }) => {
          coreProductElements = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/core-product-elements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/core-product-elements?page=0&size=20>; rel="last",<http://localhost/api/core-product-elements?page=0&size=20>; rel="first"',
              },
              body: [coreProductElements],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(coreProductElementsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CoreProductElements page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('coreProductElements');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', coreProductElementsPageUrlPattern);
      });

      it('edit button click should load edit CoreProductElements page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CoreProductElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', coreProductElementsPageUrlPattern);
      });

      it('edit button click should load edit CoreProductElements page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CoreProductElements');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', coreProductElementsPageUrlPattern);
      });

      it('last delete button click should delete instance of CoreProductElements', () => {
        cy.intercept('GET', '/api/core-product-elements/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('coreProductElements').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', coreProductElementsPageUrlPattern);

        coreProductElements = undefined;
      });
    });
  });

  describe('new CoreProductElements page', () => {
    beforeEach(() => {
      cy.visit(`${coreProductElementsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CoreProductElements');
    });

    it('should create an instance of CoreProductElements', () => {
      cy.get(`[data-cy="value"]`).type('Steel connect Producer').should('have.value', 'Steel connect Producer');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        coreProductElements = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', coreProductElementsPageUrlPattern);
    });
  });
});
